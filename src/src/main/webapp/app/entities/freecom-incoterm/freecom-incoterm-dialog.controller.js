(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_incotermDialogController', Freecom_incotermDialogController);

    Freecom_incotermDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_incoterm'];

    function Freecom_incotermDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_incoterm) {
        var vm = this;

        vm.freecom_incoterm = entity;
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
            if (vm.freecom_incoterm.id !== null) {
                Freecom_incoterm.update(vm.freecom_incoterm, onSaveSuccess, onSaveError);
            } else {
                Freecom_incoterm.save(vm.freecom_incoterm, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_incotermUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
