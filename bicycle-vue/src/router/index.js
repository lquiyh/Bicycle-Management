import {createRouter, createWebHistory} from 'vue-router'
import {useUserStore} from "@/stores/user";
import {ElMessage} from 'element-plus';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {path: '/', component: resolve => require(['./pages/login.vue'], resolve)},
    {
      path: '/login',
      name: 'Login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/user',
      name: 'User',
      component: () => import('../views/User/User.vue'),
      children: [
        {path: '/returnList', name: "ReturnList", component: () => import('../views/Rent/ReturnList.vue')},
        {path: '/rentList', name: "RentList", component: () => import('../views/Rent/RentList.vue')},
      ]
    },
    {
      path: '/admin',
      name: 'Admin',
      component: () => import('../views/Admin/Admin.vue'),
      children: [
        {path: '/bicycleList', name: "BicycleList", component: () => import('../views/Bicycle/List.vue')},
        {path: '/editBicycle', name: "EditBicycle", component: () => import('../views/Bicycle/Edit.vue')},
        {path: '/addBicycle', name: "AddBicycle", component: () => import('../views/Bicycle/Add.vue')},
        {path: '/userList', name: "UserList", component: () => import('../views/Member/UserList.vue')},
        {path: '/editUser', name: "EditUser", component: () => import('../views/Member/EditUser.vue')},
      ]
    },
    {
      path: '/superadmin',
      name: 'SuperAdmin',
      component: () => import('../views/Superadmin/Superadmin.vue')
    }
  ]
})


router.beforeEach((to, from, next) => {
  const store = useUserStore()  // 拿到用户对象信息
  const user = store.loginInfo.user
  console.log(user);
  const hasUser = user && user.id
  const noPermissionPaths = ['/login', '/register']   // 定义无需登录的路由
  if (!hasUser && !noPermissionPaths.includes(to.path)) {  // 用户没登录,  假如你当前跳转login页面，然后login页面没有用户信息，这个时候你再去往 login页面跳转，就会发生无限循环跳转
    // 获取缓存的用户数据
    //  如果to.path === '/login' 的时候   !noPermissionPaths.includes(to.path) 是返回 false的，也就不会进 next("/login")
    ElMessage.error("请先登录")
    next("/login")
  } else {
    next()
  }
})
export default router
