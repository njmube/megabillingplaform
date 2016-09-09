(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountDialogController', Taxpayer_accountDialogController);

    Taxpayer_accountDialogController.$inject = ['$timeout', '$filter', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Taxpayer_account', 'Tax_address', 'Taxpayer_certificate', 'Type_taxpayer', 'Tax_regime', 'User'];

    function Taxpayer_accountDialogController ($timeout, $filter, $scope, $stateParams, $uibModalInstance, $q, entity, Taxpayer_account, Tax_address, Taxpayer_certificate, Type_taxpayer, Tax_regime, User) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tax_addresss = Tax_address.query({filter: 'taxpayer_account-is-null'});
        $q.all([vm.taxpayer_account.$promise, vm.tax_addresss.$promise]).then(function() {
            if (!vm.taxpayer_account.tax_address || !vm.taxpayer_account.tax_address.id) {
                return $q.reject();
            }
            return Tax_address.get({id : vm.taxpayer_account.tax_address.id}).$promise;
        }).then(function(tax_address) {
            vm.tax_addresses.push(tax_address);
        });
        vm.taxpayer_certificates = Taxpayer_certificate.query({filter: 'taxpayer_account-is-null'});
        $q.all([vm.taxpayer_account.$promise, vm.taxpayer_certificates.$promise]).then(function() {
            if (!vm.taxpayer_account.taxpayer_certificate || !vm.taxpayer_account.taxpayer_certificate.id) {
                return $q.reject();
            }
            return Taxpayer_certificate.get({id : vm.taxpayer_account.taxpayer_certificate.id}).$promise;
        }).then(function(taxpayer_certificate) {
            vm.taxpayer_certificates.push(taxpayer_certificate);
        });
        vm.type_taxpayers = Type_taxpayer.query();
        vm.tax_regimes = Tax_regime.query({filtername:" "});
        var dateFormat = 'yyyy-MM-dd';
        var fromDate = $filter('date')("0000-01-01", dateFormat);
        var toDate = $filter('date')("0000-01-01", dateFormat);
        vm.users = User.query({
            filterrfc: " ",
            datefrom: fromDate,
            dateto: toDate,
            stateuser: -1,
            role: " "});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.taxpayer_account.id !== null) {
                Taxpayer_account.update(vm.taxpayer_account, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_account.save(vm.taxpayer_account, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_accountUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
