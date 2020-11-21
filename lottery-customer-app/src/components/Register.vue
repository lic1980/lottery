<template>
    <div>
        <el-form :model="customerForm" :rules="rules" label-width="80px" >
            <el-form-item label="电话" prop="tel" >
                <el-input v-model="customerForm.tel" maxlength="11"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" >
                <el-input v-model="customerForm.password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="regster()">注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import axios from 'axios'
import '@/common/const.js'
import { getUrlKey } from '@/common/utils';

export default {
    data() {
      return {
            agentId: "",
            customerForm: {
                tel: "",
                password: ""
            },
            rules: {
                tel: [{required: true,message: '请输入电话号码', trigger: 'change'}],
                password: [{required: true, message: '请输入密码', trigger: 'change'}]
            }
        }
    },
    methods: {
        regster: function() {
            let data = {
                "tel": this.customerForm.tel,
                "plainPassword": this.customerForm.password,
                "agent.id": this.agentId
            };
            axios
                .post('http://localhost:8080/api/v1/customers', data, {headers: {'Content-Type': 'application/json'}})
                .then(
                    response => {
                        sessionStorage.setItem(global.CUSTOMER_ID_KEY, response.data.id);
                        this.$router.push({path: '/bid'});
                        }
                )
                .catch(function (error) { 
                    console.log(error);
                });
        }
    },
    mounted () {
        this.agentId  = getUrlKey("agentId",window.location.href)
    }
}
</script>

<style>
ul {
    list-style: none;
    line-height: 30px;
    margin-left: 5px;
}
li {
    display: block;
    float: left;
    margin-left: 5px;
}
</style>