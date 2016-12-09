(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_data_enajenanteDialogController', Com_data_enajenanteDialogController);

    Com_data_enajenanteDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_data_enajenante', 'Com_public_notaries'];

    function Com_data_enajenanteDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Com_data_enajenante, Com_public_notaries) {
        var vm = this;
        vm.com_data_enajenante = entity;
        vm.com_public_notariess = Com_public_notaries.query({filter: 'com_data_enajenante-is-null'});
        $q.all([vm.com_data_enajenante.$promise, vm.com_public_notariess.$promise]).then(function() {
            if (!vm.com_data_enajenante.com_public_notaries || !vm.com_data_enajenante.com_public_notaries.id) {
                return $q.reject();
            }
            return Com_public_notaries.get({id : vm.com_data_enajenante.com_public_notaries.id}).$promise;
        }).then(function(com_public_notaries) {
            vm.com_public_notariess.push(com_public_notaries);
        });
        vm.load = function(id) {
            Com_data_enajenante.get({id : id}, function(result) {
                vm.com_data_enajenante = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_data_enajenanteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_data_enajenante.id !== null) {
                Com_data_enajenante.update(vm.com_data_enajenante, onSaveSuccess, onSaveError);
            } else {
                Com_data_enajenante.save(vm.com_data_enajenante, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
