<template>
    <div>
        <ul>
            <li><router-link :to="{ path: '/bid'}">旺财</router-link></li>
            <li><router-link :to="{ path: '/lottery', query: { lotteryId: 1}}">双色球</router-link></li>
            <li><router-link :to="{ path: '/lottery', query: { lotteryId: 2}}">福彩3D</router-link></li>
            <li><router-link :to="{ path: '/lottery', query: { lotteryId: 3}}">大乐透</router-link></li>
        </ul>
    </div>
</template>

<script>
export default {

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