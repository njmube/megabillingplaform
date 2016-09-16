(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Customs_infoDialogController', Customs_infoDialogController);

    Customs_infoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Customs_info', 'Concept', 'Part_concept'];

    function Customs_infoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Customs_info, Concept, Part_concept) {
        var vm = this;

        vm.customs_info = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.concepts = Concept.query();
        vm.part_concepts = Part_concept.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customs_info.id !== null) {
                Customs_info.update(vm.customs_info, onSaveSuccess, onSaveError);
            } else {
                Customs_info.save(vm.customs_info, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:customs_infoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
