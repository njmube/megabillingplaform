(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_notary_dataDialogController', Freecom_notary_dataDialogController);

    Freecom_notary_dataDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_notary_data', 'Freecom_public_notaries', 'C_pn_federal_entity'];

    function Freecom_notary_dataDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_notary_data, Freecom_public_notaries, C_pn_federal_entity) {
        var vm = this;
        vm.freecom_notary_data = entity;
        vm.freecom_public_notariess = Freecom_public_notaries.query({filter: 'freecom_notary_data-is-null'});
        $q.all([vm.freecom_notary_data.$promise, vm.freecom_public_notariess.$promise]).then(function() {
            if (!vm.freecom_notary_data.freecom_public_notaries || !vm.freecom_notary_data.freecom_public_notaries.id) {
                return $q.reject();
            }
            return Freecom_public_notaries.get({id : vm.freecom_notary_data.freecom_public_notaries.id}).$promise;
        }).then(function(freecom_public_notaries) {
            vm.freecom_public_notariess.push(freecom_public_notaries);
        });
        vm.c_pn_federal_entitys = C_pn_federal_entity.query();
        vm.load = function(id) {
            Freecom_notary_data.get({id : id}, function(result) {
                vm.freecom_notary_data = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_notary_dataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_notary_data.id !== null) {
                Freecom_notary_data.update(vm.freecom_notary_data, onSaveSuccess, onSaveError);
            } else {
                Freecom_notary_data.save(vm.freecom_notary_data, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
