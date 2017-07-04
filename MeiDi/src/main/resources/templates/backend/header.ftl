
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="${PATH}/backend">后台管理系统</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">
        <!-- /.dropdown -->
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="${PATH}/backend/editPasswordPage"><i class="fa fa-gear fa-fw"></i> 修改密码</a>
                </li>
                <li class="divider"></li>
                <li><a href="${PATH}/backend/logout"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li class="active">
                    <a href="#"><i class="fa fa-heart fa-fw"></i>项目管理<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level collapse" aria-expanded="true">
                        <li>
                            <a href="${PATH}/backend/addCommodityPage">添加商品</a>
                        </li>
                        <li>
                            <a href="${PATH}/backend/commodityListPage">商品列表</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                <li class="active">
                    <a href="#"><i class="fa fa-group fa-fw"></i>订单管理<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level collapse in" aria-expanded="true">

                        <li>
                            <a href="${PATH}/backend/orderListPage">订单列表</a>
                        </li>

                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                <li class="active">
                    <a href="#"><i class="fa fa-user fa-fw"></i>用户管理<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level collapse in" aria-expanded="true">
                        <li>
                            <a href="${PATH}/backend/userListPage">客户列表</a>
                        </li>
                        <#if bu_flag == 1>
                            <li>
                                <a href="${PATH}/backend/backendUserListPage">管理员列表</a>
                            </li>
                        </#if>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                <li>
                    <a href="#"><i class="fa fa-star fa-fw"></i>其他管理<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="${PATH}/backend/indexImage">首页轮播图</a>
                        </li>
                        <li>
                            <a href="${PATH}/backend/buyNotice/1">购买&支付说明</a>
                        </li>
                        <li>
                            <a href="${PATH}/backend/tagsPage/1">项目标签</a>
                        </li>
                        <li>
                            <a href="${PATH}/backend/tagsPage/2">认识渠道</a>
                        </li>
                        <li>
                            <a href="${PATH}/backend/interest">感兴趣项目</a>
                        </li>
                        <li>
                            <a href="${PATH}/backend/others/2">常见问题</a>
                        </li>
                        <li>
                            <a href="${PATH}/backend/others/1">关于我们</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>