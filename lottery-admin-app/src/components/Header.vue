<template>
    <div>
        <ul>
            <li><router-link :to="{ path: '/agent'}">投注站管理</router-link></li>
            <li><router-link :to="{ path: '/bid'}">投注管理</router-link></li>
            <li><router-link :to="{ path: '/customer'}">充值管理</router-link></li>
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