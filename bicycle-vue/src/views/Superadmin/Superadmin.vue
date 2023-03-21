<template>
    <div class="common-layout" sytu>
        <el-container>
            <el-header height="200px">

                <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false"
                    @select="handleSelect">
                    <div style="width: 100px; padding-left: 30px">
                        <img src="../assets/logo.svg" alt="" style="width: 60px; display: inline">
                    </div>
                    <div style="flex: 1; color: #4447ea; font-size: 30px;margin: 10px; margin-left: 25px;">
                        <b>Bicycle-Management</b>
                    </div>

                    <div class="flex-grow" />
                    <el-sub-menu index="0">
                        <template #title>{{ currentUser.username }}</template>
                        <el-menu-item index="0-1"> 修改信息</el-menu-item>
                        <el-menu-item index="0-2"> 退出登录</el-menu-item>
                    </el-sub-menu>
                </el-menu>

            </el-header>
            <el-container>
                <el-aside width="200px">
                    <div style="height: 680px;">
                        <el-menu class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"
                            active-text-color="#4447ea" default-active="1" default-openeds>
                            <el-menu-item index="1">
                                <el-icon>
                                    <Histogram />
                                </el-icon>
                                <span>统计数据</span>
                            </el-menu-item>
                            <el-menu-item index="2">
                                <el-icon>
                                    <Bicycle />
                                </el-icon>
                                <span>管理单车</span>
                            </el-menu-item>
                            <el-menu-item index="3">
                                <el-icon>
                                    <User />
                                </el-icon>
                                <span>管理用户</span>
                            </el-menu-item>
                            <el-sub-menu index="4">
                                <template #title>
                                    <el-icon><icon-menu /></el-icon>
                                    <span>查看订单</span>
                                </template>
                                <el-menu-item index="4-1">未完成订单</el-menu-item>
                                <el-menu-item index="4-2">全部订单</el-menu-item>
                            </el-sub-menu>
                        </el-menu>
                    </div>
                </el-aside>
                <el-main>
                    主页
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>



<script setup>
import { ref } from 'vue'
import { Menu as IconMenu, Message, Setting } from '@element-plus/icons-vue'
import { useUserStore } from "@/stores/user";

const currentUser = useUserStore().loginInfo.user;

</script>

<style>
.el-header {
    background-color: rgb(255, 255, 255);
    height: auto;
    padding: 0;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
    height: 680px;
}

.flex-grow {
    flex-grow: 1;
}

.el-menu-item--popup {
    width: 50px;
}
</style>