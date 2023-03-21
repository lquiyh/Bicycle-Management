<template>
    <div style="width: 80%;">
        <el-card class="box-card" style="width:50%; margin:auto;margin-top:50px;">
            <template #header>
                <div class="card-header">
                    <span>新增自行车</span>
                </div>
            </template>
            <el-form :inline="true" :model="form" :label-position="top" ref="ruleFormRef" label-width="150" :rules="rules">
                <el-form-item label="品牌" prop="brand">
                    <el-input v-model="form.brand" placeholder="请输入自行车品牌"></el-input>
                </el-form-item>
                <el-form-item label="型号" prop="model">
                    <el-input v-model="form.model" placeholder="请输入自行车型号"></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-input v-model.number="form.price" placeholder="请输入自行车的每小时租金"></el-input>
                </el-form-item>
                <el-form-item label="数量" prop="amount">
                    <el-input v-model.number="form.amount" placeholder="请输入自行车的数量"></el-input>
                </el-form-item>
            </el-form>
            <div style="text-align: center; margin-top: 30px">
                <el-button type="primary" @click="addBicycle" size="medium">提交</el-button>
            </div>
        </el-card>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import request from "@/utils/request";
import router from "@/router";
import { ElMessage, ElNotification } from 'element-plus';
const form = reactive({})
const ruleFormRef = ref()
const rules = reactive({
    brand: [
        { required: true, message: '请输入自行车品牌', trigger: 'blur' },
    ],
    model: [
        { required: true, message: '型号不能为空', trigger: 'blur' },
    ],
    price: [
        { required: true, message: '每小时租金不能为空', trigger: 'blur' },
    ],
    amount: [
        { required: true, message: '数量不能为空', trigger: 'blur' },
    ],

})
const addBicycle = () => {
    ruleFormRef.value.validate(valid => {
        if (valid) {
            // 发送表单数据给后台
            request.post('/bicycle/add', form).then(res => {
                if (res.code === '200') {
                    ElMessage.success('新增自行车成功')
                    router.push('/bicycleList')
                } else {
                    ElMessage.error(res.msg)
                }
            })
        }
    })
}
</script>

