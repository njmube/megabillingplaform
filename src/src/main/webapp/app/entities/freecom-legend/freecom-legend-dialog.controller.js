(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_legendDialogController', Freecom_legendDialogController);

    Freecom_legendDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_legend', 'Freecom_taxlegends'];

    function Freecom_legendDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_legend, Freecom_taxlegends) {
        var vm = this;

        vm.freecom_legend = entity;
        vm.clear = clear;
        vm.save = save;
        vm.freecom_taxlegends = Freecom_taxlegends.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_legend.id !== null) {
                Freecom_legend.update(vm.freecom_legend, onSaveSuccess, onSaveError);
            } else {
                Freecom_legend.save(vm.freecom_legend, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_legendUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
