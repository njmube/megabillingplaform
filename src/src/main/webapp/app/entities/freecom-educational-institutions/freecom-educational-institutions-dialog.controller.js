(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_educational_institutionsDialogController', Freecom_educational_institutionsDialogController);

    Freecom_educational_institutionsDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_educational_institutions', 'Free_cfdi', 'C_school_level'];

    function Freecom_educational_institutionsDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_educational_institutions, Free_cfdi, C_school_level) {
        var vm = this;
        vm.freecom_educational_institutions = entity;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_educational_institutions-is-null'});
        $q.all([vm.freecom_educational_institutions.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_educational_institutions.free_cfdi || !vm.freecom_educational_institutions.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_educational_institutions.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.c_school_levels = C_school_level.query();
        vm.load = function(id) {
            Freecom_educational_institutions.get({id : id}, function(result) {
                vm.freecom_educational_institutions = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_educational_institutionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_educational_institutions.id !== null) {
                Freecom_educational_institutions.update(vm.freecom_educational_institutions, onSaveSuccess, onSaveError);
            } else {
                Freecom_educational_institutions.save(vm.freecom_educational_institutions, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
