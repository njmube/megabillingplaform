(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_educational_institutionsDialogController', Com_educational_institutionsDialogController);

    Com_educational_institutionsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_educational_institutions', 'Cfdi', 'C_school_level'];

    function Com_educational_institutionsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_educational_institutions, Cfdi, C_school_level) {
        var vm = this;
        vm.com_educational_institutions = entity;
        vm.cfdis = Cfdi.query({filter: 'com_educational_institutions-is-null'});
        $q.all([vm.com_educational_institutions.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_educational_institutions.cfdi || !vm.com_educational_institutions.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_educational_institutions.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });
        vm.c_school_levels = C_school_level.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_educational_institutionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_educational_institutions.id !== null) {
                Com_educational_institutions.update(vm.com_educational_institutions, onSaveSuccess, onSaveError);
            } else {
                Com_educational_institutions.save(vm.com_educational_institutions, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
