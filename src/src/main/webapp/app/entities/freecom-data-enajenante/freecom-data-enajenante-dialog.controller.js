(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_data_enajenanteDialogController', Freecom_data_enajenanteDialogController);

    Freecom_data_enajenanteDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_data_enajenante', 'Freecom_public_notaries'];

    function Freecom_data_enajenanteDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_data_enajenante, Freecom_public_notaries) {
        var vm = this;
        vm.freecom_data_enajenante = entity;
        vm.freecom_public_notariess = Freecom_public_notaries.query({filter: 'freecom_data_enajenante-is-null'});
        $q.all([vm.freecom_data_enajenante.$promise, vm.freecom_public_notariess.$promise]).then(function() {
            if (!vm.freecom_data_enajenante.freecom_public_notaries || !vm.freecom_data_enajenante.freecom_public_notaries.id) {
                return $q.reject();
            }
            return Freecom_public_notaries.get({id : vm.freecom_data_enajenante.freecom_public_notaries.id}).$promise;
        }).then(function(freecom_public_notaries) {
            vm.freecom_public_notariess.push(freecom_public_notaries);
        });
        vm.load = function(id) {
            Freecom_data_enajenante.get({id : id}, function(result) {
                vm.freecom_data_enajenante = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_data_enajenanteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_data_enajenante.id !== null) {
                Freecom_data_enajenante.update(vm.freecom_data_enajenante, onSaveSuccess, onSaveError);
            } else {
                Freecom_data_enajenante.save(vm.freecom_data_enajenante, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
