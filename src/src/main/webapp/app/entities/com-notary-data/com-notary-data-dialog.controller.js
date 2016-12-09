(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_notary_dataDialogController', Com_notary_dataDialogController);

    Com_notary_dataDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_notary_data', 'Com_public_notaries', 'Public_notaries_federal_entity'];

    function Com_notary_dataDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Com_notary_data, Com_public_notaries, Public_notaries_federal_entity) {
        var vm = this;
        vm.com_notary_data = entity;
        vm.com_public_notariess = Com_public_notaries.query({filter: 'com_notary_data-is-null'});
        $q.all([vm.com_notary_data.$promise, vm.com_public_notariess.$promise]).then(function() {
            if (!vm.com_notary_data.com_public_notaries || !vm.com_notary_data.com_public_notaries.id) {
                return $q.reject();
            }
            return Com_public_notaries.get({id : vm.com_notary_data.com_public_notaries.id}).$promise;
        }).then(function(com_public_notaries) {
            vm.com_public_notariess.push(com_public_notaries);
        });
        vm.public_notaries_federal_entitys = Public_notaries_federal_entity.query();
        vm.load = function(id) {
            Com_notary_data.get({id : id}, function(result) {
                vm.com_notary_data = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_notary_dataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_notary_data.id !== null) {
                Com_notary_data.update(vm.com_notary_data, onSaveSuccess, onSaveError);
            } else {
                Com_notary_data.save(vm.com_notary_data, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
