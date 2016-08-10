(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_spei_thirdDialogController', Freecom_spei_thirdDialogController);

    Freecom_spei_thirdDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_spei_third', 'Freecom_spei', 'Freecom_payer', 'Freecom_beneficiary'];

    function Freecom_spei_thirdDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Freecom_spei_third, Freecom_spei, Freecom_payer, Freecom_beneficiary) {
        var vm = this;

        vm.freecom_spei_third = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.freecom_speis = Freecom_spei.query();
        vm.freecom_payers = Freecom_payer.query({filter: 'freecom_spei_third-is-null'});
        $q.all([vm.freecom_spei_third.$promise, vm.freecom_payers.$promise]).then(function() {
            if (!vm.freecom_spei_third.freecom_payer || !vm.freecom_spei_third.freecom_payer.id) {
                return $q.reject();
            }
            return Freecom_payer.get({id : vm.freecom_spei_third.freecom_payer.id}).$promise;
        }).then(function(freecom_payer) {
            vm.freecom_payers.push(freecom_payer);
        });
        vm.freecom_beneficiarys = Freecom_beneficiary.query({filter: 'freecom_spei_third-is-null'});
        $q.all([vm.freecom_spei_third.$promise, vm.freecom_beneficiarys.$promise]).then(function() {
            if (!vm.freecom_spei_third.freecom_beneficiary || !vm.freecom_spei_third.freecom_beneficiary.id) {
                return $q.reject();
            }
            return Freecom_beneficiary.get({id : vm.freecom_spei_third.freecom_beneficiary.id}).$promise;
        }).then(function(freecom_beneficiary) {
            vm.freecom_beneficiaries.push(freecom_beneficiary);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_spei_third.id !== null) {
                Freecom_spei_third.update(vm.freecom_spei_third, onSaveSuccess, onSaveError);
            } else {
                Freecom_spei_third.save(vm.freecom_spei_third, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_spei_thirdUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date_operation = false;
        vm.datePickerOpenStatus.hour = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
