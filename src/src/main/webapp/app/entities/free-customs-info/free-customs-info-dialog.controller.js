(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_customs_infoDialogController', Free_customs_infoDialogController);

    Free_customs_infoDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_customs_info', 'Free_concept', 'Free_part_concept'];

    function Free_customs_infoDialogController ($scope, $stateParams, $uibModalInstance, entity, Free_customs_info, Free_concept, Free_part_concept) {
        var vm = this;
        vm.free_customs_info = entity;
        vm.free_concepts = Free_concept.query();
        vm.free_part_concepts = Free_part_concept.query();
        vm.load = function(id) {
            Free_customs_info.get({id : id}, function(result) {
                vm.free_customs_info = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_customs_infoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_customs_info.id !== null) {
                Free_customs_info.update(vm.free_customs_info, onSaveSuccess, onSaveError);
            } else {
                Free_customs_info.save(vm.free_customs_info, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
