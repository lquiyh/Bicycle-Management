<script setup>
import { onMounted, reactive, ref } from "vue"
import { User, Lock } from '@element-plus/icons-vue'
import request from "@/utils/request";
import { ElCard, ElMessage } from "element-plus";
import { useUserStore } from "@/stores/user";
import router from "@/router";
import { storeToRefs } from "pinia";

const loginData = reactive({})
const rules = reactive({
    username: [
        { required: true, message: '请输入账号', trigger: 'blur' },
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 20, message: '密码长度在6-20位之间', trigger: 'blur' },
    ],
})
const ruleFormRef = ref()
const login = () => {
    ruleFormRef.value.validate(valid => {
        if (valid) {
            // 发送表单数据给后台
            request.post('/login', loginData).then(res => {
                if (res.code === '200') {
                    //console.log(res.data.token)
                    const userStore = useUserStore()
                    userStore.setLoginInfo(res.data)
                    
                    ElMessage.success('登录成功')
                    //const currentUser = useUserStore.loginInfo.user
                    //console.log(res)
                    router.push('/user')
                    if (res.data.user.role === 'user')
                        router.push('/user')
                    else if (res.data.user.role === 'admin') {
                        router.push('/admin')
                    } else if (res.data.user.role === 'superadmin') {
                        router.push('/superadmin')
                    }
                } else {
                    ElMessage.error(res.msg)
                }
            })
        }
    })
}

</script>

<template>

    <div style="height: 100vh; overflow: hidden; background-color: aliceblue; display: flex;">
        <div>

            <img src="@/assets/login.svg" style="width: 500px; margin: 100px; margin-top: 130px;" />

        </div>
        <el-card
            style="width: 400px; margin: 200px auto; background-color: white;  padding: 30px; border-radius: 30px;">
            <el-form ref="ruleFormRef" :model="loginData" :rules="rules" size="large" status-icon>
                <div
                    style="text-align: center; color: #4447ea; font-size: 30px; font-weight: bold; margin-bottom: 30px">
                    登 录</div>
                <el-form-item prop="username">
                    <el-input v-model="loginData.username" placeholder="请输入账号" :prefix-icon="User" />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="loginData.password" show-password placeholder="请输入密码" :prefix-icon="Lock" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" style="width: 30%;margin: auto;" @click="login" color="#4447ea" round>登
                        录</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>
