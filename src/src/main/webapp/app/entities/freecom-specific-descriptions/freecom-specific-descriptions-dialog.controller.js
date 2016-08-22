(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_specific_descriptionsDialogController', Freecom_specific_descriptionsDialogController);

    Freecom_specific_descriptionsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_specific_descriptions'];

    function Freecom_specific_descriptionsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_specific_descriptions) {
        var vm = this;

        vm.freecom_specific_descriptions = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_specific_descriptions.id !== null) {
                Freecom_specific_descriptions.update(vm.freecom_specific_descriptions, onSaveSuccess, onSaveError);
            } else {
                Freecom_specific_descriptions.save(vm.freecom_specific_descriptions, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_specific_descriptionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
