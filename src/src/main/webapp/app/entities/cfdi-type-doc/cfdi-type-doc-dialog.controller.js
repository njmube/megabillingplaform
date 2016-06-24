(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_type_docDialogController', Cfdi_type_docDialogController);

    Cfdi_type_docDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cfdi_type_doc'];

    function Cfdi_type_docDialogController ($scope, $stateParams, $uibModalInstance, entity, Cfdi_type_doc) {
        var vm = this;
        vm.cfdi_type_doc = entity;
        vm.load = function(id) {
            Cfdi_type_doc.get({id : id}, function(result) {
                vm.cfdi_type_doc = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:cfdi_type_docUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.cfdi_type_doc.id !== null) {
                Cfdi_type_doc.update(vm.cfdi_type_doc, onSaveSuccess, onSaveError);
            } else {
                Cfdi_type_doc.save(vm.cfdi_type_doc, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
