<template>
    <div class="common-layout" sytu>
        <el-container>
            <el-header height="200px">

                <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false"
                    @select="handleSelect">
                    <div style="width: 100px; padding-left: 30px">
                        <img src='@/assets/logo.svg' alt="" style="width: 60px; display: inline">
                    </div>
                    <div style="flex: 1; color: #4447ea; font-size: 30px;margin: 10px; margin-left: 25px;">
                        <b>Bicycle-Management</b>
                    </div>

                    <div class="flex-grow" />
                    <el-dropdown>
                        <div class="el-dropdown-link" style="line-height: 60px">
                            <span style="font-size: 14px; margin-left: 45px">{{ currentUser.username }}</span>
                            <el-icon class="el-icon&#45;&#45;right">
                                <arrow-down />
                            </el-icon>
                        </div>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item>
                                    <div>修改信息</div>
                                </el-dropdown-item>
                                <el-dropdown-item>
                                    <div @click="logout">退出登录</div>
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </el-menu>

            </el-header>
            <el-container>
                <el-aside width="200px">
                    <div style="height: 680px;">
                        <el-menu class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"
                            active-text-color="#4447ea" router default-active="$route.path">
                            <el-menu-item index="/rentList">
                                <el-icon>
                                    <Bicycle />
                                </el-icon>
                                <span>租赁单车</span>
                            </el-menu-item>
                            <el-menu-item index="/returnList">
                                <el-icon>
                                    <Switch />
                                </el-icon>
                                <span>归还单车</span>
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
                    <router-view key="$route.fullPass"></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>



<script setup>
import { ref } from 'vue'
import { Menu as IconMenu, Message, Setting } from '@element-plus/icons-vue'
import { useUserStore } from "@/stores/user";
import router from "@/router";

const currentUser = useUserStore().loginInfo.user;
const logout = () => {
    localStorage.removeItem("user")
    router.push('/login')
}
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