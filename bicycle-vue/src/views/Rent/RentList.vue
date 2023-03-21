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
                <el-table-column prop="id" label="编号" width="80"></el-table-column>
                <el-table-column prop="brand" label="单车品牌"></el-table-column>
                <el-table-column prop="model" label="单车型号"></el-table-column>
                <el-table-column prop="rate" label="评分" width="250" sortable>
                    <template #default="scope">
                        <el-rate v-model="scope.row.rate" :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                            disabled-void-color="#e5e6eb" disabled size="large" show-score score-template="{value}分">
                        </el-rate>
                    </template>
                </el-table-column>
                <el-table-column prop="price" label="租金" sortable>
                    <template #default="scope">
                        <spam>
                            {{ scope.row.price }}元/小时
                        </spam>
                    </template>
                </el-table-column>
                <el-table-column prop="amount" label="剩余数量" sortable></el-table-column>
                <el-table-column label="操作" width="200">
                    <template #default="scope">
                        <!--          scope.row 就是当前行数据-->
                        <el-popconfirm style="margin-left: 5px" title="您确定租赁此自行车吗？" @confirm="rent(scope.row.id)">
                            <template #reference>
                                <el-button type="primary">租赁</el-button>
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
    load()
})
const data = reactive({
    tableData: [],
    total: 0,
    params: {
        pageNum: 1,
        pageSize: 8,
        brand: '',
        model: '',
        rate: '',
        amount: '',
    }
}
)
const load = () => {
    request.get('/rent/page', {
        params: data.params
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
const rent = (id) => {
    request.post('/rent/rentBicycle', {
        userId: currentUser.id,
        bicycleId: id,
    }).then(res => {
        if (res.code === '200') {
            ElMessage.success('租借成功')
        } else {
            ElMessage.error(res.msg)
        }
        load()
    })
}
</script>
