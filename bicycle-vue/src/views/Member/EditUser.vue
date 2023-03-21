<template>
    <div style="width: 80%;">
        <el-card class="box-card" style="width:50%;  margin:auto;margin-top:50px;">
            <template #header>
                <div class="card-header">
                    <span>编辑用户信息</span>
                </div>
            </template>
            <el-form :inline="true" :model="form.value" :label-position="top" label-width="150">
                <el-form-item label="用户编号" prop="id">
                    <el-input v-model="form.id" disabled></el-input>
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="真实姓名" prop="realname">
                    <el-input v-model="form.realname" placeholder="请输入用户真实姓名"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="form.phone" placeholder="请输入手机号"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
                </el-form-item> 
                <el-form-item label="地址" prop="address">
                    <el-input v-model="form.address" placeholder="请输入用户地址"></el-input>
                </el-form-item> 
            </el-form>
            <div style="text-align: center; margin-top: 30px">
                <el-button type="primary" @click="save" size="medium">提交</el-button>
            </div>
        </el-card>


    </div>
</template>

<script setup>
import request from "@/utils/request";
import router from "@/router";
import { onMounted, ref, reactive } from "vue"
import { ElMessage } from "element-plus";
import { useRoute } from 'vue-router';
let form = ref({})
const route = useRoute()
onMounted(() => {
    const id = route.query.id
    request.get("/user/" + id).then(res => {
        form.value = res.data
    })
    console.log(form)
})
const save = () => {
    request.put('/user/update', form.value).then(res => {
        if (res.code === '200') {
            ElMessage.success('更新成功')
            router.push("/userList")
        } else {
            ElMessage.error(res.msg)
        }
    })
}
</script>

