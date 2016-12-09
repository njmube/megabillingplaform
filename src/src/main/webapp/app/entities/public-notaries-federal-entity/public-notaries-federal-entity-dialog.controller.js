(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Public_notaries_federal_entityDialogController', Public_notaries_federal_entityDialogController);

    Public_notaries_federal_entityDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Public_notaries_federal_entity'];

    function Public_notaries_federal_entityDialogController ($scope, $stateParams, $uibModalInstance, entity, Public_notaries_federal_entity) {
        var vm = this;
        vm.public_notaries_federal_entity = entity;
        vm.load = function(id) {
            Public_notaries_federal_entity.get({id : id}, function(result) {
                vm.public_notaries_federal_entity = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:public_notaries_federal_entityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.public_notaries_federal_entity.id !== null) {
                Public_notaries_federal_entity.update(vm.public_notaries_federal_entity, onSaveSuccess, onSaveError);
            } else {
                Public_notaries_federal_entity.save(vm.public_notaries_federal_entity, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
