export default{
    state:{
        //控制菜单栏状态
        isCollapse:false,
        //面包屑功能
        tabsList:[
            {
                path:'/start',
                name:'start',
                label:'首页',
                icon:'home',
                url:'start'
            }
        ]
    },
    mutations:{
        collapseChange(state){
            state.isCollapse = !state.isCollapse
        },
        menuChange(state,val){
            // 更新面包屑的数据
            if(val.label !== '首页'){
                const index = state.tabsList.findIndex(item => item.name === val.name)
                if( index == -1){
                    state.tabsList.push(val)
                }
            }
        }
    }
}