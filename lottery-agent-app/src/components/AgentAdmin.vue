<template>
    <div>
        <div style="overflow:hidden;height:40px;">
            <div style="float:left;width:60px;">电话：</div>
            <div style="float:left;">{{ agent.tel}}</div>
        </div>
        <div  style="overflow:hidden;height:40px;">
            <div style="float:left;width:60px;">地址：</div>
            <div style="float:left;">{{ agent.address}}</div>
        </div>
        <div  style="overflow:hidden;height:40px;">
            <div style="float:left;width:60px;">微信：</div>
            <div style="float:left;">{{ agent.weixin}}</div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    data () {
        return {
            agent: {}
        }
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

    }
}
</script>

<style>

</style>