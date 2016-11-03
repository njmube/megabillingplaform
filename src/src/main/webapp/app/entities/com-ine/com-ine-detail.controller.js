(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ineDetailController', Com_ineDetailController);

    Com_ineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_ine', 'Cfdi', 'C_committee_type', 'C_process_type'];

    function Com_ineDetailController($scope, $rootScope, $stateParams, entity, Com_ine, Cfdi, C_committee_type, C_process_type) {
        var vm = this;
        vm.com_ine = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_ineUpdate', function(event, result) {
            vm.com_ine = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
