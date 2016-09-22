(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AddUserController', AddUserController);

    AddUserController.$inject = ['$scope', '$filter', 'Principal', 'User','$stateParams', '$uibModal', 'Taxpayer_account', '$uibModalInstance', '$q', 'entity', 'DataUtils', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code','Type_taxpayer'];

    function AddUserController ($scope, $filter, Principal, User, $stateParams, $uibModal, Taxpayer_account, $uibModalInstance, $q, entity, DataUtils, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.showInfo = false;
        vm.edit = null;
        vm.filterrfc = null;
        vm.search = search;
        vm.users = null;

        vm.add = add;

        vm.load = function(id) {
            Taxpayer_account.get({id : id}, function(result) {
                vm.taxpayer_account = result;
            });
        };

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        function add(user){
            vm.taxpayer_account.users[vm.taxpayer_account.users.length] = user;
            Taxpayer_account.update(vm.taxpayer_account, onSaveSuccess, onSaveError);
        }

        function search() {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')("0000-01-01", dateFormat);
            var toDate = $filter('date')("0000-01-01", dateFormat);
            var stateuser = -1;
            var role = " ";
            var filterrfc = " ";
            if(vm.filterrfc != null && vm.filterrfc != ""){
                filterrfc = vm.filterrfc;
                vm.showInfo = false;
                User.query({
                        page: vm.page - 1,
                        size: 10,
                        filterrfc: filterrfc,
                        datefrom: fromDate,
                        dateto: toDate,
                        stateuser: stateuser,
                        role: role},
                    function (result, headers) {
                        vm.users = result;
                    });
            }else{
                vm.showInfo = true;
            }
        }

        var onSaveSuccess = function (result) {
            vm.taxpayer_account =  result;
            vm.showInfo = false;
            $uibModalInstance.close({
                taxpayer_account: vm.taxpayer_account
            });
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };


        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    }
})();


