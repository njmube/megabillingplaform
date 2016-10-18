(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('MessegeUserController', MessegeUserController);

    MessegeUserController.$inject = ['$uibModalInstance', 'Principal', 'User'];

    function MessegeUserController ($uibModalInstance, Principal, User) {
        var vm = this;
        vm.user = {};
        vm.account = null;
        var today = new Date();
        vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        vm.restDate = restDate;

        load();

        function load(){
            Principal.identity().then(function(account) {
                vm.account = account;

                if(vm.account != null){
                    User.get({login: vm.account.login}, function(result) {
                        vm.user = result;
                    });
                }
            });
        }

        vm.close = function () {
            $uibModalInstance.dismiss('cancel');
        };

        function restDate(datecreated){
            var fechacreado = new Date(datecreated);
            var dias = (vm.toDate.getTime() - fechacreado.getTime())/86400000;
            var resto = 0;
            if(dias < 15){
                resto = 15 - dias;
            }
            return Math.round(resto);
        }
    }
})();
