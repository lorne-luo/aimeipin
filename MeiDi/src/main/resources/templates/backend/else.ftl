<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>后台管理系统</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="../dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../bower_components/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

      
    <link href="../dist/css/common.css" rel="stylesheet">
    <link href="../dist/js/msgbox/msgbox.css" rel="stylesheet" type="text/css"/>
    <link href="../dist/css/page.css" rel="stylesheet" type="text/css" />
    <script src="../dist/js/jquery-1.11.2.min.js"></script>
    <script src="../dist/js/DialogClass.js"></script>
    <script src="../dist/js/jquery.myPagination.js"></script>
    <script src="../dist/js/jquery.validate.min.js"></script>
    <script src="../dist/js/ajaxfileupload.js"></script>
    <script src="../dist/js/msgbox/msgbox.js"></script>
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="../dist/js/sb-admin-2.js"></script>

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">后台管理系统</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> 设置</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
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
                       
                       
                        <li>
                            <a href="#"><i class="fa fa-list-alt fa-fw"></i> 商品管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                            	<li>
                                    <a href="shangp.html">添加商品</a>
                                </li>
                                <li>
                                    <a href="shangplist.html">查看商品列表</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-heart fa-fw"></i> 特惠类别管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="tehui.html">创建特惠类别</a>
                                </li>
                                <li>
                                    <a href="tehuilist.html">查看特惠列表</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                         <li>
                            <a href="#"><i class="fa fa-star fa-fw"></i> 福袋类别管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                              
                                <li>
                                    <a href="fu.html">创建福袋类别</a>
                                </li>
                                <li>
                                    <a href="fulist.html">查看福袋列表</a>
                                </li>
                                
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                         <li>
                            <a href="#"><i class="fa fa-group fa-fw"></i> 拼团类别管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                
                                <li>
                                    <a href="tuan.html">创建拼团类别</a>
                                </li>
                                <li>
                                    <a href="tuanlist.html">查看拼团列表</a>
                                </li>
                                
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                         <li>
                            <a href="#"><i class="fa fa-edit fa-fw"></i> 其他<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                
                                <li>
                                    <a href="tuan.html">自定义特殊服务项</a>
                                </li>
                                <li>
                                    <a href="tuan.html">自定义感兴趣项目</a>
                                </li>
                                <li>
                                    <a href="tuan.html">自定义渠道</a>
                                </li>
                                 <li>
                                    <a href="elselubo.html">自定义首页轮播图</a>
                                </li>
                                <li>
                                    <a href="tuanlist.html">自定义常见问题</a>
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

        <div id="page-wrapper">
           
             <div class="col-lg-10">
                <div class="row">
                    <div class="col-lg-12">
                        <h4 class="page-header">自定义服务项目</h4>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <div class="col-lg-10">
                    <form class=" form-inline" role="form">
                        
                        <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="form-group input-group">
                                    <input type="text" class="form-control"  id="bigapartname" name="bigapartname">
                                    <span class="input-group-btn">
                                        <button class="btn btn-default" type="button" id="creatnewbig">创建大分类
                                        </button>
                                    </span>
                                </div>
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <!-- Nav tabs -->
                                <ul class="nav nav-pills">
                                    <li class="active"><a href="#home-pills" data-toggle="tab" aria-expanded="false">头</a> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                    </li>
                                    <li><a href="#profile-pills" data-toggle="tab">脚</a><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                    </li>
                                    <li><a href="#messages-pills" data-toggle="tab">腿</a><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                    </li>
                                    <li class=""><a href="#settings-pills" data-toggle="tab" aria-expanded="false">手</a><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                   
                                </ul>

                                <!-- Tab panes -->
                                <div class="tab-content">
                                    <div class="tab-pane fade active in" id="home-pills">
                                        <h4></h4>
                                        <div class=" myitems clearfix mb20">
                                           <span class="alert alert-info pr" role="alert" tipid=0>
                                              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true" onclick="delname(this)">×</span></button>
                                              <strong>商品</strong>
                                            </span>
                                            <span class="alert alert-info pr" role="alert" tipid=1>
                                              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true" onclick="delname(this)">×</span></button>
                                              <strong>商品</strong>
                                            </span>
                                            
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="profile-pills">
                                       <h4></h4>
                                        <div class=" myitems clearfix mb20">
                                           <span class="alert alert-info pr" role="alert" tipid=0>
                                              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true" onclick="delname(this)">×</span></button>
                                              <strong>商品</strong>
                                            </span>
                                            <span class="alert alert-info pr" role="alert" tipid=1>
                                              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true" onclick="delname(this)">×</span></button>
                                              <strong>商品</strong>
                                            </span>
                                            
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="messages-pills">
                                        <h4></h4>
                                        <div class=" myitems clearfix mb20">
                                           <span class="alert alert-info pr" role="alert" tipid=0>
                                              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true" onclick="delname(this)">×</span></button>
                                              <strong>商品</strong>
                                            </span>
                                            <span class="alert alert-info pr" role="alert" tipid=1>
                                              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true" onclick="delname(this)">×</span></button>
                                              <strong>商品</strong>
                                            </span>
                                            
                                        </div>
                                    </div>
                                    
                                    <div class="tab-pane fade" id="settings-pills">
                                        <h4></h4>
                                        <div class=" myitems clearfix mb20">
                                           
                                            
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group input-group">
                                    <input type="text" class="form-control "  name="servicename" id="servicename">
                                    <span class="input-group-btn">
                                        <button class="btn btn-default" type="button" id="creatnew">创建小分类
                                        </button>
                                    </span>
                                </div>
                            </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                     
                    </form>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

</body>

</html>
<script type="text/javascript">

</script>