(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope', 'AlertService','$scope', '$uibModal', 'Transactions_history','Taxpayer_transactions','User', 'Principal', 'Tracemg','LoginService','$filter', 'Taxpayer_account', 'Request_taxpayer_account'];

    function HomeController ($rootScope,AlertService, $scope, $uibModal, Transactions_history, Taxpayer_transactions,  User, Principal, Tracemg,  LoginService, $filter, Taxpayer_account, Request_taxpayer_account) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.isNoAdmin = null;
        vm.showDraw = null;
        vm.showDrawMonth = null;
        var today = new Date();
        vm.page = 1;
        vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        vm.user = {};
        vm.taxpayer_transactions = {};
        vm.restDate = restDate;
        vm.showmenu = 'OK';
        vm.ringsaccount = ringsaccount;
        vm.days = 0;
        vm.messegeUser = messegeUser;
        vm.pie = pie;
        vm.drawAccount = drawAccount;
        vm.drawMontWeek = drawMontWeek;
        vm.loadTracemgAccount = loadTracemgAccount;

        var dateFormat = 'yyyy-MM-dd';
        var fromDate = $filter('date')("0000-01-01", dateFormat);
        var toDate = $filter('date')("0000-01-01", dateFormat);
        var principal = " ";
        var auditEventType = " ";
        var ip = " ";
        vm.audits = null;
        vm.login = null;

        vm.taxpayer_account = null;
        vm.taxpayer_accountsta = null;
        vm.totaltrans = 0;
        vm.transactions_available = 0;
        vm.transactions_spent = 0;
        vm.transactions_adquired = 0;
        vm.transactions_transfer = 0;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
            vm.login = LoginService.open;
        });

        getAccount();

        messegeUser();

        loadTracemgAccount();

        function getAccount() {

			$('#sidebar').attr('class','sidebar h-sidebar navbar-collapse collapse');

            Principal.identity().then(function(account) {
                vm.account = account;
				vm.isAuthenticated = Principal.isAuthenticated;

                if(vm.account != null){
                    vm.isNoAdmin = vm.account.authorities.indexOf('ROLE_ADMIN') == -1;

                    if(!vm.isNoAdmin){
                        $('#sidebar').attr('class','sidebar responsive sidebar-fixed sidebar-scroll');
                        $('#sidebar').attr('data-sidebar','true');
                        $('#sidebar').attr('data-sidebar-scroll','true');
                        $('#sidebar').attr('data-sidebar-hover','true');
                        $('.nav-wrap, .scroll-content').css('max-height',($(window).height() - 55) + 'px');
                        $('.scroll-track.scroll-active').css('height',($(window).height() - 55) + 'px');
                        $('#sidebar-shortcuts').attr('style','');
                        $('#sidebar-options').attr('style','');

                        var dateFormat = 'yyyy-MM-dd';
                        var fromDate = $filter('date')("0000-01-01", dateFormat);
                        var toDate = $filter('date')("0000-01-01", dateFormat);
                        User.query({
                                filterrfc: " ",
                                datefrom: fromDate,
                                dateto: toDate,
                                stateuser: -1,
                                role: " ",
                                filterlogin: " "},
                            function (result) {
                                vm.totalUser = result.length;
                            });

                        Request_taxpayer_account.query({
                            datefrom: fromDate,
                            dateto: toDate,
                            request_state: -1
                        }, function(data){
                            vm.totalRequest_taxpayer_accounts = data.length;
                        });

                        Taxpayer_transactions.query({
                            idaccount:0
                        }, function(data){

                            vm.totalTaxpayer_transactions = 0;
                            for(var i = 0; i < data.length; i++){
                                vm.totalTaxpayer_transactions += data[i].transactions_available;
                            }
                        });
                    }

                    User.get({login: vm.account.login}, function(result) {
                        vm.user = result;
                    });

                    vm.tracemgs = Tracemg.query({
                        page: 0,
                        size: 10,
                        fromDate: fromDate,
                        toDate: toDate,
                        principal: principal,
                        auditEventType: auditEventType,
                        ip: ip
                    });

                    vm.taxpayer_accounts = Taxpayer_account.query({
                        page: 0,
                        size: 100
                    });

                    vm.request_taxpayer_accounts = Request_taxpayer_account.query({
                        page: 0,
                        size: 10,
                        datefrom: fromDate,
                        dateto: toDate,
                        request_state: 1
                    });

                    vm.isAfilitated = vm.account.authorities.indexOf('ROLE_AFILITATED') != -1;

                    if(vm.isAfilitated){

                        Taxpayer_transactions.query({
                            page: 0,
                            size: 100,
                            idaccount: 0
                        }, onSuccess1, onError1);
                    }
                }

            });
        }

        function loadTracemgAccount(){
            Principal.identity().then(function(account) {

                if (account != null) {
                    vm.isAfilitated = account.authorities.indexOf('ROLE_AFILITATED') != -1;

                    if(vm.isAfilitated){
                        Tracemg.queryAccount({
                            page: 0,
                            size: 10,
                            principal: " "
                        }, onSuccess12, onError12);
                    }
                }
            });
        }

        function  onSuccess12(data){
            vm.tracemgAccounts = data;
        }
        function onError12(){
            AlertService.error(error.data.message);
        }

        function messegeUser(){
            if($rootScope.mess == null){
                Principal.identity().then(function(account) {
                    if(account != null) {
                        vm.isUser = account.authorities.indexOf('ROLE_USER') != -1;
                        vm.isNoAdmin = account.authorities.indexOf('ROLE_ADMIN') == -1;

                        if (vm.isUser && vm.isNoAdmin) {
                            $uibModal.open({
                                templateUrl: 'app/home/messegeUser.html',
                                controller: 'MessegeUserController',
                                controllerAs: 'vm',
                                backdrop: true,
                                size: '',
                                resolve: {
                                    entity: function () {
                                        return 0;
                                    }
                                }
                            }).result.then(function () {

                                }, function () {
                                });
                        }
                    }
                });
            }
        }

        function restDate(datecreated){
            var fechacreado = new Date(datecreated);
            var dias = (vm.toDate.getTime() - fechacreado.getTime())/86400000;
            var resto = 0;
            if(dias < 7){
                resto = 7 - dias;
            }
            if(dias >= 7){
                vm.showmenu = null;
            }
            return Math.round(resto);
        }

        function onSuccess1(data){
            vm.taxpayer_transactions = data;
            var totaltra = 0;
            if(vm.taxpayer_transactions != null && vm.taxpayer_accounts != null){
                for(var i=0;i < vm.taxpayer_accounts.length; i++){
                    for(var j=0;j < vm.taxpayer_transactions.length; j++){
                        if(vm.taxpayer_transactions[j].taxpayer_account.id == vm.taxpayer_accounts[i].id)
                        vm.taxpayer_accounts[i].transactions_available = vm.taxpayer_transactions[j].transactions_available;
                        totaltra += vm.taxpayer_transactions[j].transactions_available;
                    }
                }
                if(totaltra == 0){
                    $uibModal.open({
                        templateUrl: 'app/entities/taxpayer-account/messWelcome.html',
                        controller: 'MessWelcomeController',
                        controllerAs: 'vm',
                        backdrop: true,
                        size: ''
                    }).result.then(function () {}, function () {});
                }
            }
        }

        function onError1(error){
            AlertService.error(error.data.message);
        }

        function ringsaccount(account){

        }

        function pie(){
            var placeholder = $('#piechart-placeholder1').css({'width': '90%' , 'height': '150px'});
            var data = [
                { label: "Habilitados",  data: 40, color: "#68BC31"},
                { label: "Gastados",  data: 60, color: "#2091CF"}
            ]
            $.plot(placeholder, data, {
                series: {
                    pie: {
                        show: true,
                        tilt:0.8,
                        highlight: {
                            opacity: 0.25
                        },
                        stroke: {
                            color: '#fff',
                            width: 2
                        },
                        startAngle: 2
                    }
                },
                legend: {
                    show: true,
                    position: "ne",
                    labelBoxBorderColor: null,
                    margin:[-30,15]
                }
                ,
                grid: {
                    hoverable: true,
                    clickable: true
                }
            });
        }

        function drawAccount(account){
            vm.taxpayer_accountsta = account;
            vm.showDrawMonth = null;
            Taxpayer_transactions.query({
                idaccount: vm.taxpayer_accountsta.id
            }, function(data){
                vm.totaltrans = 0;
                vm.transactions_available = 0;
                vm.transactions_spent = 0;
                vm.totaltrans += data[0].transactions_available + data[0].transactions_spent;
                vm.transactions_available = data[0].transactions_available;
                vm.transactions_spent = data[0].transactions_spent;
                vm.showDraw = 'OK';

                var placeholder = $('#piechart-placeholder1').css({'width': '90%' , 'height': '150px'});
                var data = [
                    { label: "Habilitados",  data: vm.transactions_available, color: "#68BC31"},
                    { label: "Gastados",  data: vm.transactions_spent, color: "#2091CF"}
                ]
                $.plot(placeholder, data, {
                    series: {
                        pie: {
                            show: true,
                            tilt:0.8,
                            highlight: {
                                opacity: 0.25
                            },
                            stroke: {
                                color: '#fff',
                                width: 2
                            },
                            startAngle: 2
                        }
                    },
                    legend: {
                        show: true,
                        position: "ne",
                        labelBoxBorderColor: null,
                        margin:[-30,15]
                    }
                    ,
                    grid: {
                        hoverable: true,
                        clickable: true
                    }
                });

            });
        }

        function drawMontWeek(month){
            vm.showDraw = null;
            var idaccount = 0;
            if(vm.taxpayer_accountsta != null)
            {
                idaccount = vm.taxpayer_accountsta.id;
            }
            Transactions_history.query({
                idaccount: idaccount,
                month: month
            }, function(data){
                vm.totaltrans = 0;
                vm.transactions_adquired = 0;
                vm.transactions_spent = 0;
                vm.transactions_transfer = 0;
                for(var i = 0; i < data.length; i++){
                    var quantity = 0;
                    quantity = parseInt(data[i].quantity);
                    vm.totaltrans += quantity;

                    if(data[i].type_transaction.id == 1){
                        vm.transactions_adquired += quantity;
                    }
                    if(data[i].type_transaction.id == 2){
                        vm.transactions_spent += quantity;
                    }
                    if(data[i].type_transaction.id == 3){
                        vm.transactions_transfer += quantity;
                    }
                }
                vm.showDrawMonth = 'OK';

                var placeholder = $('#piechart-placeholder1').css({'width': '90%' , 'height': '150px'});
                var data = [
                    { label: "Adquiridos",  data: vm.transactions_adquired, color: "#68BC31"},
                    { label: "Gastados",  data: vm.transactions_spent, color: "#2091CF"},
                    { label: "Transferidos",  data: vm.transactions_transfer, color: "#C2101C"}
                ]
                $.plot(placeholder, data, {
                    series: {
                        pie: {
                            show: true,
                            tilt:0.8,
                            highlight: {
                                opacity: 0.25
                            },
                            stroke: {
                                color: '#fff',
                                width: 2
                            },
                            startAngle: 2
                        }
                    },
                    legend: {
                        show: true,
                        position: "ne",
                        labelBoxBorderColor: null,
                        margin:[-30,15]
                    }
                    ,
                    grid: {
                        hoverable: true,
                        clickable: true
                    }
                });

            });
        }

    }
})();
