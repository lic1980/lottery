import Vue from 'vue'
import VueRouter from 'vue-router'
import ElementUI from 'element-ui';
import App from './App.vue'
import routers from './router/index.js'
import store from './store/index.js'

import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  routes: routers
 })

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
