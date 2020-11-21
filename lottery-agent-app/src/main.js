import Vue from 'vue'
import ElementUI from 'element-ui';
import VueRouter from 'vue-router'
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import routers from './router/index.js'

Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  routes: routers
 })

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
