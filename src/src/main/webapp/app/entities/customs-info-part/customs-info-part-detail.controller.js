(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Customs_info_partDetailController', Customs_info_partDetailController);

    Customs_info_partDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Customs_info_part', 'Part_concept'];

    function Customs_info_partDetailController($scope, $rootScope, $stateParams, entity, Customs_info_part, Part_concept) {
        var vm = this;
        vm.customs_info_part = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:customs_info_partUpdate', function(event, result) {
            vm.customs_info_part = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
