<template>
    <div>
        <el-form :model="agentForm" :rules="rules" label-width="80px" >
            <el-form-item label="电话" prop="tel" >
                <el-input v-model="agentForm.tel" maxlength="11"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" >
                <el-input v-model="agentForm.password"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="tel" required>
                <el-input v-model="agentForm.address" maxlength="11"></el-input>
            </el-form-item>
            <el-form-item label="微信" prop="tel" required>
                <el-input v-model="agentForm.weixin" maxlength="11"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="regster()">注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import axios from 'axios'
import '../common/const.js'

export default {
    data() {
      return {
            agentForm: {
                tel: "",
                password: "",
                address: "",
                weixin: ""
            },
            rules: {
                tel: [{required: true,message: '请输入电话号码', trigger: 'change'}],
                password: [{required: true, message: '请输入密码', trigger: 'change'}],
                address: [{required: true, message: '请输入地址', trigger: 'change'}],
                weixin: [{required: true, message: '请输入微信', trigger: 'change'}]
            }
        }
    },
    methods: {
        regster: function() {
            let data = {
                "tel": this.agentForm.tel,
                "plainPassword": this.agentForm.password,
                "address": this.agentForm.address,
                "weixin": this.agentForm.weixin
            };
            axios
                .post('http://localhost:8080/api/v1/agents', data, {headers: {'Content-Type': 'application/json'}})
                .then(
                    response => {
                        sessionStorage.setItem(global.AGENT_ID_KEY, response.data.id);
                        this.$router.push({path: '/bid'});
                        }
                )
                .catch(function (error) { 
                    console.log(error);
                });
        }
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