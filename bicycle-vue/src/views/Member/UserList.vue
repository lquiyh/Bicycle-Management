<template>
    <div>
        <!--    搜索表单-->
        <div style="margin-bottom: 20px; margin-top:30px; ">
            <el-input style="width: 240px" placeholder="请输入用户名" v-model="data.params.username"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入手机号" v-model="data.params.phone"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>
                搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>
                重置</el-button>
        </div>
        <div style="margin-top: 50px;">
            <el-table :data="data.tableData" stripe row-key="id" default-expand-all :row-style="{ height: '60px' }">
                <el-table-column prop="id" label="用户id" width="70"></el-table-column>
                <el-table-column prop="username" label="用户名" width="100"></el-table-column>
                <el-table-column prop="realname" label="真实姓名" width="100"></el-table-column>
                <el-table-column prop="phone" label="电话号" width="150"></el-table-column>
                <el-table-column prop="email" label="电子邮件"> </el-table-column>
                <el-table-column prop="address" label="地址"></el-table-column>
                <el-table-column prop="loginDate" label="最近登陆日期"></el-table-column>
                <el-table-column label="操作" width="100">
                    <template #default="scope">
                        <!--          scope.row 就是当前行数据-->
                        <el-button type="primary" @click="edit(scope.row.id)">编辑</el-button>
                        <!-- <el-popconfirm style="margin-left: 5px" title="您确定删除这行数据吗？" @confirm="del(scope.row.id)">
                            <template #reference>
                                <el-button type="danger">删除</el-button>
                            </template>
                        </el-popconfirm> -->
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
const router = useRouter()
onMounted(() => {
    load()
})
const data = reactive({
    tableData: [],
    total: 0,
    params: {
        pageNum: 1,
        pageSize: 8,
        username: '',
        realname: '',
        phone: '',
        address: '',
        email: '',
        loginDate: '',
    }
}
)
const load = () => {
    request.get('/user/page', {
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
const del = (id) => {
    request.delete("/user/delete/" + id).then(res => {
        if (res.code === '200') {
            ElMessage.success('删除成功')
            load()
        } else {
            ElMessage.error(res.msg)
        }
    })
}
const edit = (id) => {
    router.push({
        path: '/editUser',
        query: {
            id: id
        }
    })
}
// export default {
//     name: 'BicycleList',
//     data() {
//         return {
//             tableData: [],
//             total: 0,
//             params: {
//                 pageNum: 1,
//                 pageSize: 8,
//                 brand: '',
//                 model: '',
//                 rate: '',
//                 amount: '',
//             }
//         }
//     },
//     created() {
//         this.load()
//     },
//     methods: {
//         load() {
//             request.get('/bicycle/page', {
//                 params: this.params
//             }).then(res => {
//                 if (res.code === '200') {
//                     this.tableData = res.data.list
//                     this.total = res.data.total
//                 }
//             })
//         },
//         reset() {
//             this.params = {
//                 pageNum: 1,
//                 pageSize: 8,
//                 brand: '',
//                 model: ''
//             }
//             this.load()
//         },
//         handleCurrentChange(pageNum) {
//             // 点击分页按钮触发分页
//             this.params.pageNum = pageNum
//             this.load()
//         },
//         del(id) {
//             request.delete("/bicycle/delete/" + id).then(res => {
//                 if (res.code === '200') {
//                     this.$notify.success('删除成功')
//                     this.load()
//                 } else {
//                     this.$notify.error(res.msg)
//                 }
//             })
//         },
//     }
// }
</script>
