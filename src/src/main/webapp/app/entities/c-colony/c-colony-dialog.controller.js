(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_colonyDialogController', C_colonyDialogController);

    C_colonyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_colony', 'C_location'];

    function C_colonyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, C_colony, C_location) {
        var vm = this;

        vm.c_colony = entity;
        vm.clear = clear;
        vm.save = save;
        vm.c_locations = C_location.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.c_colony.id !== null) {
                C_colony.update(vm.c_colony, onSaveSuccess, onSaveError);
            } else {
                C_colony.save(vm.c_colony, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:c_colonyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
