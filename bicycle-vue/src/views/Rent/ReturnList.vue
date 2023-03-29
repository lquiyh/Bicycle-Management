<template>
    <div>
        <!--    搜索表单-->
        <div style=" font-size: 40px;
                    font-weight: 600;
                    background-image: linear-gradient(to left, #4447ea, #409eff);
                    color: transparent;
                    background-clip: text;
                    -webkit-background-clip: text;
                    width: 500px;
                    text-align:center;
                     ">Bicycles to be returned
        </div>
        <div style="margin-top: 50px;">
            <el-table :data="data.tableData" stripe row-key="id" default-expand-all :row-style="{ height: '60px' }">
                <el-table-column prop="bicycleBrand" label="单车品牌"></el-table-column>
                <el-table-column prop="bicycleModel" label="单车型号"></el-table-column>
                <el-table-column prop="price" label="租金" sortable>
                    <template #default="scope">
                        <spam>
                            {{ scope.row.price }}元/小时
                        </spam>
                    </template>
                </el-table-column>
                <el-table-column prop="rentDate" label="租借日期" width="250" sortable></el-table-column>
                <el-table-column prop="rentHours" label="租借时长" sortable>
                    <template #default="scope">
                        <spam>
                            {{ scope.row.rentHours }}小时
                        </spam>
                    </template>
                </el-table-column>
                <el-table-column prop="cost" label="租金" sortable>
                    <template #default="scope">
                        <spam>
                            {{ scope.row.cost }}元
                        </spam>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template #default="scope">
                        <!--          scope.row 就是当前行数据-->
                        <el-popconfirm style="margin-left: 5px" title="您确定归还此自行车吗？" @confirm="open(scope.row.id)">
                            <template #reference>
                                <el-button type="primary">归还</el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!--    分页-->
        <div style="margin-top: 20px;margin-left:auto">
            <el-pagination background :current-page="data.params.pageNum" :page-size="data.params.pageSize"
                layout="prev, pager, next" @current-change="handleCurrentChange" :total="data.total">
            </el-pagination>
        </div>

    </div>
</template>

<script setup>
import request from "@/utils/request";
import { ElMessage, ElMessageBox } from 'element-plus';
import { onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
const centerDialogVisible = ref(false)
const router = useRouter()
const currentUser = useUserStore().loginInfo.user;
onMounted(() => {
    data.params.userId = currentUser.id
    load()
})
const data = reactive({
    tableData: [],
    total: 0,
    params: {
        pageNum: 1,
        pageSize: 8,
        userId: '',
        brand: '',
        model: '',
        price: '',
        cost: '',
        rentDate: '',
    }
}
)
const load = () => {
    request.get('/rent/returnPage', {
        params: data.params,
        userId: currentUser.id
    }).then(res => {
        if (res.code === '200') {
            data.tableData = res.data.list
            data.total = res.data.total
        }
    })
    console.log(data)
}
const reset = () => {
    data.params = {
        pageNum: 1,
        pageSize: 8,
        brand: '',
        model: ''
    }
    load()
}
const handleCurrentChange = (pageNum) => {
    data.params.pageNum = pageNum
    load()
}
const returnBicycle = (id) => {
    request.post('/rent/returnBicycle', {
        userId: currentUser.id,
        id: id
    }).then(res => {
        if (res.code === '200') {
            ElMessage.success('归还成功')
        } else {
            ElMessage.error(res.msg)
        }
        load()
    })
}
const open = (id) => {
    ElMessageBox.confirm(
        id,
        'Warning',
        {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
        }
    )
        .then(() => {
            ElMessage({
                type: 'success',
                message: 'Delete completed',
            })
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: 'Delete canceled',
            })
        })
}
</script>
