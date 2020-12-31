<template>
    <div>
        <div>
            <div></div>
        </div>
        <div>
            <div>代理列表：</div> 
            <div>
                <el-table :data="agents" style="width: 100%">
                    <el-table-column prop="tel" label="电话">
                    </el-table-column>   
                    <el-table-column prop="address" label="地址">
                    </el-table-column>   
                </el-table>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    data () {
        return {
            agents: {}
        }
    },
    methods: {
    },
    mounted () {
        let adminId = sessionStorage.getItem(global.ADMIN_ID_KEY);
        axios
            .get('http://localhost:8080/api/v1/admins/' + adminId +'/agents')
            .then(response => (this.agents = response.data.content))
            .catch(function (error) { 
                console.log(error);
            });

    }
}
</script>

<style>

</style>