
export default class Draw {

    constructor () {
        this.x = 0;
        this.y = 0;
        this.points = [];
        this.flag = false;
    }

    setup (canvas, option) {
        option = option || {};

        if (typeof canvas === 'string' || canvas.constructor === HTMLCanvasElement) {
            option.canvas = canvas;
        } else {
            option = canvas || option;
        }

        this.fill = option.fill || null;
        this.line = option.line || 'black';

        this.width = option.width || 1;
        this.quality = option.quality || 0.5;
        this.type = option.type || 'image/webp';
        this.canvas = typeof option.canvas === 'string' ? document.body.querySelector(option.canvas) : option.canvas;

        if (!option.canvas) {
            throw new Error('Signer - canvas option required');
        }

        this.context = this.canvas.getContext('2d', {
            desynchronized: true,
            preserveDrawingBuffer: true
        });

        this.canvas.style.cursor = 'crosshair';
        this.canvas.width = this.canvas.parentElement.clientWidth;
        this.canvas.height = this.canvas.parentElement.clientHeight;

        if (this.fill) {
            this.context.fillStyle = this.fill;
            this.context.fillRect(0, 0, this.canvas.width, this.canvas.height);
        }

        this.canvas.addEventListener('mouseup', this.tick.bind(this, this.change.bind(this, 'up')), false);
        this.canvas.addEventListener('mouseout', this.tick.bind(this, this.change.bind(this, 'out')), false);
        this.canvas.addEventListener('mousedown', this.tick.bind(this, this.change.bind(this, 'down')), false);
        this.canvas.addEventListener('mousemove', this.tick.bind(this, this.change.bind(this, 'move')), false);

        this.canvas.addEventListener('touchend', this.tick.bind(this, this.change.bind(this, 'up')), false);
        this.canvas.addEventListener('touchmove', this.tick.bind(this, this.change.bind(this, 'move')), false);
        this.canvas.addEventListener('touchstart', this.tick.bind(this, this.change.bind(this, 'down')), false);
    }

    tick (method, events) {

        if (events.type !== 'resize' && events.cancelable) {
            events.preventDefault();
        }

        window.requestAnimationFrame(method.bind(this, events));
    }

    up () {
        this.flag = false;
        this.points.length = 0;
    }

    out () {
        this.flag = false;
        this.points.length = 0;
    }

    down (x, y) {
        this.flag = true;
        this.points.push({ x, y });
    }

    move (x, y) {
        if (!this.flag) return;
        this.points.push({ x, y });

        this.context.beginPath();
        this.context.moveTo(this.points[0].x, this.points[0].y);

        for (var i = 1; i < this.points.length; i++) {
            this.context.lineTo(this.points[i].x, this.points[i].y);
        }

        this.context.strokeStyle = this.line;
        this.context.shadowColor = this.line;
        this.context.lineWidth = this.width;
        this.context.lineCap = 'round';
        this.context.lineJoin = 'round';
        this.context.shadowBlur = 1;
        this.context.stroke();
        this.context.closePath();
    }

    change (type, e) {
        const bounds = this.canvas.getBoundingClientRect();
        const tx = e.touches ? e.touches[0].clientX : e.clientX;
        const ty = e.touches ? e.touches[0].clientY : e.clientY;
        const x = tx - bounds.left;
        const y = ty - bounds.top;
        this[type](x, y);
    }

    erase () {
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);

        if (this.fill) {
            this.context.fillRect(0, 0, this.canvas.width, this.canvas.height);
            this.context.fillStyle = this.fill;
        }

    }

    async reader (blob, type) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onerror = (error) => reject(error);
            reader.onload = () => resolve(reader.result);
            reader[type](blob);
        });
    }

    async image (data) {
        return new Promise((resolve, reject) => {
            const image = new Image();
            image.onerror = (error) => reject(error);
            image.onload = () => resolve(image);
            if (typeof data === 'string') image.src = data;
            else if (data instanceof Blob) image.src = URL.createObjectURL(data);
            else throw new Error('Draw - invalid image data type');
        });
    }

    async blob (blob) {
        if (blob) {
            const image = await this.image(blob);
            this.context.drawImage(image, 0, 0);
        } else {
            return new Promise(resolve => this.canvas.toBlob(resolve, this.type, this.quality));
        }
    }

    async url (url) {
        if (url) {
            const image = await this.image(url);
            this.context.drawImage(image, 0, 0);
        } else {
            const blob = await this.blob();
            return this.reader(blob, 'readAsDataURL');
        }
    }

    // stringToBase64Url (data) {
    //     return window
    //         .btoa(data)
    //         .replace(/\+/g, '-')
    //         .replace(/\//g, '_')
    //         .replace(/=/g, '');
    // }
    //
    // base64UrlToBase64 (data) {
    //     return (data + '==='.slice((data.length + 3) % 4))
    //         .replace(/-/g, '+')
    //         .replace(/_/g, '/');
    // }
    //
    // async url (url) {
    //     if (url) {
    //         const index = url.indexOf('base64,')+7;
    //         url = `${url.slice(0, index)}${this.base64UrlToBase64(url.slice(index))}`;
    //         const image = await this.image(url);
    //         this.context.drawImage(image, 0, 0);
    //     } else {
    //         const blob = await this.blob();
    //         const binary = await this.reader(blob, 'readAsBinaryString');
    //         const base64url = this.stringToBase64Url(binary);
    //         return `data:${blob.type};base64,${base64url}`;
    //     }
    // }

    async text () {
        const blob = await this.blob();
        return this.reader(blob, 'readAsText');
    }

    async binary () {
        const blob = await this.blob();
        return this.reader(blob, 'readAsBinaryString');
    }

    async buffer () {
        const blob = await this.blob();
        return this.reader(blob, 'readAsArrayBuffer');
    }

}
