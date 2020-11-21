<template>
    <div>
        <div>
            <div>账户余额：{{ customer.deposit }}</div>
        </div>
        <div>
            <div>最近投注：</div> 
            <div>
                <el-table :data="bids" style="width: 100%">
                    <el-table-column  label="投注项">
                        <template slot-scope="scope">  
                            <li v-for="(option, index) in scope.row.options" v-bind:key="index">
                                <span v-if="index == 0 || option.item.name != scope.row.options[index -1].item.name">
                                    {{ option.item.name }}
                                </span>
                                {{ option.text }}
                            </li>  
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="投注时间">
                    </el-table-column>
                    <el-table-column label="状态">
                        <template slot-scope="scope">  
                            <span v-if="scope.row.status=='ACKED'">
                                正在处理
                            </span>
                            <span v-if="scope.row.status=='NEW'">
                                未处理
                            </span>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import '../common/const.js'

export default {
    data () {
        return {
            customer: { "id": "", "deposit": 0 },
            bids: []
        }
    },
    mounted () {
        let cusId = sessionStorage.getItem(global.CUSTOMER_ID_KEY);
        axios
            .get('http://localhost:8080/api/v1/customers/' + cusId)
            .then(response => (this.customer = response.data))
            .catch(function (error) { 
                console.log(error);
            });
        axios
            .get('http://localhost:8080/api/v1/customers/'+ cusId +'/bids?page=1&size=10')
            .then(response => {
                    this.bids = response.data.content;
                })
            .catch(function (error) { 
                console.log(error);
            });
    }
}
</script>

<style>

</style>