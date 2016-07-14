(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_customs_infoDialogController', Free_customs_infoDialogController);

    Free_customs_infoDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_customs_info'];

    function Free_customs_infoDialogController ($scope, $stateParams, $uibModalInstance, entity, Free_customs_info) {
        var vm = this;
        vm.free_customs_info = entity;
		
        vm.load = function(id) {
            Free_customs_info.get({id : id}, function(result) {
                vm.free_customs_info = result;
            });
        };

        vm.save = function () {
            vm.isSaving = true;
            
			$uibModalInstance.close({
				number_doc: vm.free_customs_info.number_doc,
				date: vm.free_customs_info.date,
				customs: vm.free_customs_info.customs,
				id: null
			});
			
			vm.isSaving = true;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
