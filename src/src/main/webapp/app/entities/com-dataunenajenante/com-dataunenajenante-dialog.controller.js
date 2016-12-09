(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataunenajenanteDialogController', Com_dataunenajenanteDialogController);

    Com_dataunenajenanteDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_dataunenajenante', 'Com_data_enajenante'];

    function Com_dataunenajenanteDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Com_dataunenajenante, Com_data_enajenante) {
        var vm = this;
        vm.com_dataunenajenante = entity;
        vm.com_data_enajenantes = Com_data_enajenante.query({filter: 'com_dataunenajenante-is-null'});
        $q.all([vm.com_dataunenajenante.$promise, vm.com_data_enajenantes.$promise]).then(function() {
            if (!vm.com_dataunenajenante.com_data_enajenante || !vm.com_dataunenajenante.com_data_enajenante.id) {
                return $q.reject();
            }
            return Com_data_enajenante.get({id : vm.com_dataunenajenante.com_data_enajenante.id}).$promise;
        }).then(function(com_data_enajenante) {
            vm.com_data_enajenantes.push(com_data_enajenante);
        });
        vm.load = function(id) {
            Com_dataunenajenante.get({id : id}, function(result) {
                vm.com_dataunenajenante = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_dataunenajenanteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_dataunenajenante.id !== null) {
                Com_dataunenajenante.update(vm.com_dataunenajenante, onSaveSuccess, onSaveError);
            } else {
                Com_dataunenajenante.save(vm.com_dataunenajenante, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
