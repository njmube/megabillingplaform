(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_accreditation_iepsDetailController', Com_accreditation_iepsDetailController);

    Com_accreditation_iepsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_accreditation_ieps', 'C_tar', 'Cfdi'];

    function Com_accreditation_iepsDetailController($scope, $rootScope, $stateParams, entity, Com_accreditation_ieps, C_tar, Cfdi) {
        var vm = this;
        vm.com_accreditation_ieps = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_accreditation_iepsUpdate', function(event, result) {
            vm.com_accreditation_ieps = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
