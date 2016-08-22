(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_seriesDialogController', C_type_seriesDialogController);

    C_type_seriesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_type_series'];

    function C_type_seriesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, C_type_series) {
        var vm = this;

        vm.c_type_series = entity;
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
            if (vm.c_type_series.id !== null) {
                C_type_series.update(vm.c_type_series, onSaveSuccess, onSaveError);
            } else {
                C_type_series.save(vm.c_type_series, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:c_type_seriesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
