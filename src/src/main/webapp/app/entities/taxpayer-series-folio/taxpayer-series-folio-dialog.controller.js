(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_series_folioDialogController', Taxpayer_series_folioDialogController);

    Taxpayer_series_folioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Taxpayer_series_folio', 'Taxpayer_account'];

    function Taxpayer_series_folioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Taxpayer_series_folio, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_series_folio = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.taxpayer_accounts = Taxpayer_account.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.taxpayer_series_folio.id !== null) {
                Taxpayer_series_folio.update(vm.taxpayer_series_folio, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_series_folio.save(vm.taxpayer_series_folio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_series_folioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date_creation = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
