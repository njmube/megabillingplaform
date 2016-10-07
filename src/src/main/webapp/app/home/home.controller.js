(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Taxpayer_transactions','User', 'Principal', 'Tracemg','LoginService','$filter', 'Taxpayer_account', 'Request_taxpayer_account'];

    function HomeController ($scope, Taxpayer_transactions,  User, Principal, Tracemg,  LoginService, $filter, Taxpayer_account, Request_taxpayer_account) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.isNoAdmin = null;
        var today = new Date();
        vm.page = 1;
        vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        vm.user = {};
        vm.taxpayer_transactions = {};
        vm.restDate = restDate;
        vm.showmenu = 'OK';
        vm.ringsaccount = ringsaccount;

        var dateFormat = 'yyyy-MM-dd';
        var fromDate = $filter('date')("0000-01-01", dateFormat);
        var toDate = $filter('date')("0000-01-01", dateFormat);
        var principal = " ";
        var auditEventType = " ";
        var ip = " ";


        vm.audits = null;
        vm.login = null;

        vm.taxpayer_account = null;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
            vm.login = LoginService.open;
        });

        getAccount();

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
                    }

                    User.get({login: vm.account.login}, function(result) {
                        vm.user = result;
                    });
                    //vm.resto = vm.toDate.getTime();
                    //var creado = new Date(vm.user.createdDate.getFullYear(), vm.user.createdDate.getMonth(), vm.user.createdDate.getDate() + 1);


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

                    Taxpayer_transactions.query({
                        page: 0,
                        size: 100,
                        idaccount: 0
                    }, onSuccess1, onError1);

                }
            });
        }


        function restDate(datecreated){
            var fechacreado = new Date(datecreated);
            var dias = (vm.toDate.getTime() - fechacreado.getTime())/86400000;
            var resto = 0;
            if(dias < 15){
                resto = 15 - dias;
            }
            if(dias >= 15){
                vm.showmenu = null;
            }
            return Math.round(resto);
        }

        function onSuccess1(data){
            vm.taxpayer_transactions = data;
            if(vm.taxpayer_transactions != null && vm.taxpayer_accounts != null){
                for(var i=0;i < vm.taxpayer_accounts.length; i++){
                    for(var j=0;j < vm.taxpayer_transactions.length; j++){
                        if(vm.taxpayer_transactions[j].taxpayer_account.id == vm.taxpayer_accounts[i].id)
                        vm.taxpayer_accounts[i].transactions_available = vm.taxpayer_transactions[j].transactions_available;
                    }
                }
            }
        }

        function onError1(error){
            AlertService.error(error.data.message);
        }

        function ringsaccount(account){

        }
    }
})();
