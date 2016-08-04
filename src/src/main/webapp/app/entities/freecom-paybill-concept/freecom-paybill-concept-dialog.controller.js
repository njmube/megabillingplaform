(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_paybill_conceptDialogController', Freecom_paybill_conceptDialogController);

    Freecom_paybill_conceptDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_paybill_concept', 'Freecom_storeroom_paybill'];

    function Freecom_paybill_conceptDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_paybill_concept, Freecom_storeroom_paybill) {
        var vm = this;
        vm.freecom_paybill_concept = entity;
        vm.freecom_storeroom_paybills = Freecom_storeroom_paybill.query();
        vm.load = function(id) {
            Freecom_paybill_concept.get({id : id}, function(result) {
                vm.freecom_paybill_concept = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_paybill_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_paybill_concept.id !== null) {
                Freecom_paybill_concept.update(vm.freecom_paybill_concept, onSaveSuccess, onSaveError);
            } else {
                Freecom_paybill_concept.save(vm.freecom_paybill_concept, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_expedition = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
