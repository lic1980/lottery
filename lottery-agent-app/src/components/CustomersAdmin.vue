<template>
    <div>
        <div>
            <div></div>
        </div>
        <div>
            <div>客户列表：</div> 
            <div>
                <el-table :data="customers" style="width: 100%">
                    <el-table-column prop="tel" label="客户电话">
                    </el-table-column>   
                    <el-table-column prop="deposit" label="余额">
                    </el-table-column>   
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button
                            size="mini"
                            @click="openDepositDiag(scope.row)">充值</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <el-dialog
            title="充值"
            :visible.sync="dialogVisible"
            width="350px"
            :before-close="handleClose">
            <div>
                <el-form>
                 <el-form-item label="客户电话：" label-width="100px">
                    <el-input v-model="customer.tel" size="small" :disabled="true"></el-input>
                 </el-form-item>
                </el-form>
            </div>
            <div>
                <el-form>
                 <el-form-item label="客户现值：" label-width="100px">
                    <el-input v-model="customer.deposit" size="small" :disabled="true"></el-input>
                 </el-form-item>
                </el-form>
            </div>
            <div>
                <el-form>
                 <el-form-item label="客户充值：" label-width="100px">
                    <el-input-number v-model="amount" size="small" :precision="2" :step="1" :max="1000"></el-input-number>
                 </el-form-item>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitDeposit()" :disabled="startDeposit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    data () {
        return {
            amount: 0,
            agent: {},
            dialogVisible: false,
            customer: {},
            customers: [],
            startDeposit: false,
        };
    },
    methods: {
        handleClose(){
            this.dialogVisible = true;
            this.customer = {};
            this.amount = 1;
        },
        openDepositDiag(row) {
            this.amount = 1;
            this.customer = row;
            this.dialogVisible = true;
        },
        submitDeposit() {
            this.startDeposit = true;
            let data = {
                "customer": {"id": this.customer.id},
                "agent": {"id": this.agent.id},
                "amount": this.amount
            };
            axios
                .post('http://localhost:8080/api/v1/agents/'+this.agent.id+'/customers/' +this.customer.id+ '/deposits', data, {headers: {'Content-Type': 'application/json'}})
                .then(response => {
                    let deposit = response.data;
                    for (let cus of this.customers) {
                        if (cus.id == this.customer.id) {
                           cus.deposit =  cus.deposit + deposit.amount;
                        } 
                    }

                    this.startDeposit = false;
                })
                .catch(error => { 
                    console.log(error);
                    this.startDeposit = false;
                });
            this.dialogVisible = false;
        }
    },
    mounted () {
        let agentId = sessionStorage.getItem(global.AGENT_ID_KEY);
        axios
            .get('http://localhost:8080/api/v1/agents/' + agentId)
            .then(response => (this.agent = response.data))
            .catch(function (error) { 
                console.log(error);
            });
        axios
            .get('http://localhost:8080/api/v1/agents/'+ agentId + '/customers?page=1&size=10')
            .then(response => {
                    this.customers = response.data.content;
                })
            .catch(function (error) { 
                console.log(error);
            });
    }
}
</script>

<style>

</style>