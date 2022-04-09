<template>
  <a-layout-header class="header">
    <div class="logo" />

    <a class="login-menu" v-show="user.id">
          <span>Welcome：{{user.name}}</span>
    </a>
    <a class="login-menu" v-show="!user.id" @click="showLoginModal">
              <span>login</span>
    </a>

    <a-menu
        theme="dark"
        mode="horizontal"
        v-model:selectedKeys="selectedKeys1"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="home">
        <router-link to="/">Home</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/user">
        <router-link to="/admin/user">User Management</router-link>
      </a-menu-item>
      <a-menu-item key="adminEbook">
        <router-link to="/admin/ebook">Ebook Management</router-link>
      </a-menu-item>
      <a-menu-item key="admin/category">
        <router-link to="/admin/category">Category Management</router-link>
      </a-menu-item>
      <a-menu-item key="about">
        <router-link to="/about">About</router-link>
      </a-menu-item>



    </a-menu>


     <a-modal
          title="login"
          v-model:visible="loginModalVisible"
          :confirm-loading="loginModalLoading"
          @ok="login"
        >
          <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="login name">
              <a-input v-model:value="loginUser.loginName" />
            </a-form-item>
            <a-form-item label="password">
              <a-input v-model:value="loginUser.password" type="password" />
            </a-form-item>
          </a-form>
        </a-modal>
  </a-layout-header>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from 'vue';
import axios from 'axios';
  import { message } from 'ant-design-vue';
  import store from "@/store";

  declare let hexMd5: any;
  declare let KEY: any;

export default defineComponent({
  name: 'the-header',
  setup () {

        // save info after login
        const user = computed(() => store.state.user);

        // for login

        const loginUser = ref({
          loginName: "test1",
          password: "test123"
        });
        const loginModalVisible = ref(false);
        const loginModalLoading = ref(false);
        const showLoginModal = () => {
          loginModalVisible.value = true;
        };

        // 登录
        const login = () => {
          console.log("start logging in");
                  loginModalLoading.value = true;
                  loginUser.value.password = hexMd5(loginUser.value.password + KEY);
                  axios.post('/user/login', loginUser.value).then((response) => {
                    loginModalLoading.value = false;
                    const data = response.data;
                    if (data.success) {
                      loginModalVisible.value = false;
                      message.success("login successfully！");
                      store.commit("setUser", data.content);
                    } else {
                      message.error(data.message);
                    }
                  });
        };

        return {
          loginModalVisible,
          loginModalLoading,
          showLoginModal,
          loginUser,
          login,
          user
        }
      }
});
</script>

<style>
  .login-menu {
    float: right;
    color: white;
  }
</style>
