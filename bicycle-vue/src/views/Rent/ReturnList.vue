<template>
    <div>
        <!--    搜索表单-->
        <div style="margin-bottom: 20px; margin-top:30px; ">
            <el-input style="width: 240px" placeholder="请输入单车品牌" v-model="data.params.brand"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入单车型号" v-model="data.params.model"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>
                搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>
                重置</el-button>
        </div>
        <div style="margin-top: 50px;">
            <el-table :data="data.tableData" stripe row-key="id" default-expand-all :row-style="{ height: '60px' }">
                <el-table-column prop="bicycleBrand" label="单车品牌" ></el-table-column>
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
                        <el-popconfirm style="margin-left: 5px" title="您确定归还此自行车吗？" @confirm="returnBicycle(scope.row.id)">
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
import { ElMessage } from "element-plus";
import { onMounted, reactive, ref } from "vue"
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";

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
</script>
