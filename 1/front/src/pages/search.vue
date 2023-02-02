<template>

    <el-main>
        <div class="cover" v-for="i in re" :key="i.vId">
           <img class="show" :src="i.cover" @click="Gotoplay(i)">
           <div class="vname">
            <p>{{ i.vName }}</p>
           </div>
        </div>
        <!-- <div class="cover" v-for="item in re" :key="item.vId" :index="item.classify" v-if="item.classify == 'Python'">
            <div>
                <img class="show" style="cursor: pointer;" :src="item.cover" @click="Gotoplay(item)" />
                <div class="vname">
                    <p>{{ item.vName }}</p>
                </div>
            </div>
        </div> -->
    </el-main>

</template>


<script>

export default {
    name: 'search',
    data() {
        return {
            re: []
        }
    },
    created() {
        this.re = this.$store.state.tab.search;
    },
    methods: {
        Gotoplay(i) {
            console.log(i);
            this.$store.state.tab.videopath = i;
            location.href = "http://localhost:8081/#/play"
        },
    }
}
</script>

<style lang="less">
html,
body,
p {
    margin: 0;
    padding: 0;
}

.cover {
    margin-left: 10%;
    display: inline-block;
    align-content: center;
    align-items: center;

    .show {
        width: 150px;
        height: 255px;
        padding: 10px;
        padding-left: 0;
        padding-right: 20px;
        object-fit: fill;
    }
}
</style>
