(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_apawDetailController', Com_apawDetailController);

    Com_apawDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_apaw', 'Cfdi', 'C_well_type', 'C_acquired_title', 'C_features_work_piece'];

    function Com_apawDetailController($scope, $rootScope, $stateParams, entity, Com_apaw, Cfdi, C_well_type, C_acquired_title, C_features_work_piece) {
        var vm = this;
        vm.com_apaw = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_apawUpdate', function(event, result) {
            vm.com_apaw = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
