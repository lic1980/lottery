<template>
    <div>
        <div>
            <div>账户余额：{{ customer.deposit }}</div>
        </div>
        <div>
            <div style="line-height: 36px;">
                <div >
                    彩票名称：{{ lotteryRound.lottery.name }}
                </div>
                <div>
                    开奖日期：{{ lotteryRound.openingTime }}
                </div>
                <div style="overflow:hidden" v-for="(optionsItemId, itemIndex) in optionsByItemId.entries()" v-bind:key="itemIndex">
                    <div></div>
                    <div style="float:left;">
                        {{ itemByItemId.get(optionsItemId[0]).name }}：
                    </div>
                    <div style="float:left;margin:2px;" v-for="(option, optionIndex) of optionsItemId[1]" v-bind:key="optionIndex">
                        <el-button v-on:click="selectOption(option)" v-bind:class="optionsSelected.includes(option)?'select': 'unselect' " size="small" circle>
                            <div style="width: 16px;">
                            {{ option.text }}
                            </div>
                        </el-button>
                    </div>
                </div>
                <div style="overflow:hidden">
                    <div style="float:left;">
                            投注倍数：
                    </div>
                    <div  style="float:left;">
                        <el-input maxlength="5" v-model="times" placeholder="倍数" size="small" min="1"></el-input>
                    </div>
                </div>
                <div style="overflow:hidden">
                    <div style="float:left;">
                            单注费用：
                    </div>
                    <div style="float:left;">
                            {{ fee }}
                    </div>
                </div>
                <div>
                    <div>
                        <el-button v-on:click="submit()" size="small" v-bind:disabled="fee == 0">
                            确定
                        </el-button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    data () {
        return {
            fee: 0,
            current: null,
            times: 1,
            customer: { "id": "", "deposit": 0 },
            lotteryRound: {"lottery": {"name":""}},
            optionsByItemId: new Map(),
            itemByItemId: new Map(),
            optionsSelected: []
        }
    },
    methods: {
        selectOption: function (optionSelected) {
            let optionsSelectedTemp = [...this.optionsSelected]
            var position = optionsSelectedTemp.indexOf(optionSelected);
            if (position < 0) {
                optionsSelectedTemp.push(optionSelected);
            } else {
                optionsSelectedTemp.splice(position, 1);
            }
            // count options selected by item
            var optionSelectedNumByItemId = new Map();
            for (var i = 0; i < optionsSelectedTemp.length; i++) {
                var option = optionsSelectedTemp[i];
                var itemId = option.item.id;
                if ( optionSelectedNumByItemId.has(itemId)) {
                    optionSelectedNumByItemId.set(itemId, optionSelectedNumByItemId.get(itemId) + 1);
                } else {
                    optionSelectedNumByItemId.set(itemId, 1 );
                }
            }

            console.log("###1 " + optionSelectedNumByItemId.size)
            console.log("###2 " + JSON.stringify(this.lotteryRound.lottery))
            // count fee
            if (optionSelectedNumByItemId.size >= this.lotteryRound.lottery.minItemNum)  {
                let baseFee = this.lotteryRound.lottery.baseFee;
                let totalFee = 1;
                
                for  (let [itemId, num] of optionSelectedNumByItemId) {
                    let item = this.itemByItemId.get(itemId);
                    let minOptionNum = item.minOptionNum;
                    let optionSelectedNum = num;
                    if (optionSelectedNum < minOptionNum) {
                        totalFee = 0;
                    } else {
                        // count combination, like C(7,6)
                        totalFee = totalFee * (this.factorial(optionSelectedNum, (optionSelectedNum - minOptionNum + 1))/this.factorial(minOptionNum,1));
                    }
                } 
                this.fee = totalFee * baseFee;
            } else {
                 this.fee = 0;
            }
            this.optionsSelected = optionsSelectedTemp;
        },
        factorial: function(max, min) {
            if (min > max) {
                return 0;
            }
            let result = max;
            for (let i = min; i< max; i++){
                result = i * result;
            }
            return result;
        },
        submit: function() {
            let cusId = sessionStorage.getItem(global.CUSTOMER_ID_KEY);
            let totalFee = this.fee * this.times;
            if (totalFee > this.customer.deposit) {
                this.$message("余额不足");
                return;
            }
            if (totalFee > global.FEE_MAX) {
                this.$message("超过最大投注额");
                return;
            }
            let data = {
                "customer": {"id": cusId},
                "lotteryRound": {"id": this.lotteryRound.id},
                "times": this.times,
                "options": this.optionsSelected
            };
            axios
                .post('http://localhost:8080/api/v1/customers/' + cusId +'/bids', data, {headers: {'Content-Type': 'application/json'}})
                .then(
                    response => {
                        this.$message("投注成功");
                        this.customer.deposit = this.customer.deposit - response.data.fee;
                    }
                )
                .catch(function (error) { 
                    console.log(error);
                });
        }
    },
    mounted () {
        let cusId = sessionStorage.getItem(global.CUSTOMER_ID_KEY);
        let lotteryId = this.$route.query.lotteryId;
        axios
            .get('http://localhost:8080/api/v1/customers/' + cusId)
            .then(response => (this.customer = response.data))
            .catch(function (error) { 
                console.log(error);
            });
        axios
            .get('http://localhost:8080/api/v1/lotteries/' + lotteryId + '/rounds?status=active')
            .then(response => {
                    if (response.data.content.length > 0) {
                        this.lotteryRound = response.data.content[0];
                    }
                })
            .catch(function (error) { 
                console.log(error);
            });
        axios
            .get('http://localhost:8080/api/v1/lotteries/' + lotteryId + '/options')
            .then(response => {
                    var options = response.data;
                    var optionsByitem = [];
                    var optionsByItemId = new Map();
                    for (var i = 0; i < options.length; i ++) {
                        var option = options[i];
                        var item = option.item;
                        if (optionsByItemId.has(item.id)) {
                            optionsByItemId.get(item.id).push(option);
                        } else {
                            optionsByitem = [];
                            optionsByitem.push(option);
                            optionsByItemId.set(item.id, optionsByitem);

                            this.itemByItemId.set(item.id, item);
                        }
                    }
                    this.optionsByItemId = optionsByItemId;
                })
            .catch(function (error) { 
                console.log(error);
            });
    }
}
</script>

<style>
.select{
    background-color: red !important;
    color:white !important;
}
.select:hover{
    background-color: red !important;
    color:white !important;
}
.select:focus{
    background-color: red !important;
    color:white !important;
}
.unselect{
    background-color: white !important;
    border-color: #DCDFE6 !important;
}
.unselect:hover{
    background-color: white !important;
    border-color: #DCDFE6 !important;
    color:black !important;
}
.unselect:focus{
    background-color: white !important;
    border-color: #DCDFE6 !important;
    color:black !important;
}
</style>