<template>
    <div style="width: 80%;">
        <el-card class="box-card" style="width:50%;  margin:auto;margin-top:50px;">
            <template #header>
                <div class="card-header">
                    <span>编辑自行车信息</span>
                </div>
            </template>
            <el-form :inline="true" :model="form.value" :label-position="top" label-width="150">
                <el-form-item label="自行车编号" prop="id">
                    <el-input v-model="form.id" disabled></el-input>
                </el-form-item>
                <el-form-item label="品牌" prop="brand">
                    <el-input v-model="form.brand" placeholder="请输入自行车品牌"></el-input>
                </el-form-item>
                <el-form-item label="型号" prop="model">
                    <el-input v-model="form.model" placeholder="请输入自行车型号"></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-input v-model="form.price" placeholder="请输入自行车的每小时租金"></el-input>
                </el-form-item>
                <el-form-item label="数量" prop="amount">
                    <el-input v-model="form.amount" placeholder="请输入自行车的数量"></el-input>
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
import { onMounted, ref,reactive } from "vue"
import { ElMessage } from "element-plus";
import { useRoute } from 'vue-router';
let form = ref({})
const route=useRoute()
onMounted(() => {
    const id = route.query.id
    request.get("/bicycle/" + id).then(res => {
        form.value = res.data
    })
    console.log(form)
})
const save = () => {
    request.put('/bicycle/update', form.value).then(res => {
        if (res.code === '200') {
            ElMessage.success('更新成功')
            router.push("/bicycleList")
        } else {
            ElMessage.error(res.msg)
        }
    })
}
</script>

